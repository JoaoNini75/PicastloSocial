import React, { useEffect, useState } from "react";
import { Box, CircularProgress, Pagination } from "@mui/material";
import PostCard from "../Post/PostCard";
import { UserControllerApi, Configuration } from "../../api";
import { useSelector } from "react-redux";
import { GlobalState } from "../../store";

export interface TimelineArgs {
  type: string;
  groupId: number | undefined
}

export const Timeline = ({type, groupId}: TimelineArgs) => {
  const username = useSelector((state: GlobalState) => state.session.username);
  const cardsPerPage = 5;
  const [currentPage, setCurrentPage] = useState(1);
  const [posts, setPosts] = useState<any[]>([]);
  const [totalPosts, setTotalPosts] = useState(0);
  const [loading, setLoading] = useState(true);

  const fetchPosts = async (page: number) => {
    try {
      const api = new UserControllerApi(new Configuration({
        basePath: "",
        credentials: "include",
      }));

      if (type == "group") {
        if (!groupId)
          return (<h2>oh no</h2>);

        const response = await api.getGroupPosts({
          groupID: parseInt("10" + groupId.toString()),
          page: page - 1,
          size: cardsPerPage,
        });

        if (response.content && response.totalElements) {
          setPosts(response.content);
          setTotalPosts(response.totalElements);
        }

      } else {
      const response = await api.getPublicPosts({
        page: page - 1,
        size: cardsPerPage,
      });

      const { content, totalElements } = response;

      if (username != "") {
        const responseFriends = await api.getFriendsPosts({
          page: page - 1,
          size: cardsPerPage,
        });

        const responseUserPosts = await api.getUserPosts({
          username: username,
          page: page - 1,
          size: cardsPerPage,
        });
        
        const userContent = responseUserPosts.content || [];
        const friendsContent = responseFriends.content || [];
        const publicContent = content || [];
        const publicTotalElements = totalElements || 0;
        
        const allPostIds = new Set(publicContent.map(post => post.id));
        
        const uniqueFriendsPosts = friendsContent.filter(
          friendPost => !allPostIds.has(friendPost.id)
        );
        
        uniqueFriendsPosts.forEach(post => allPostIds.add(post.id));
        
        const uniqueUserPosts = userContent.filter(
          userPost => !allPostIds.has(userPost.id)
        );
        
        const mergedContent = [...publicContent, ...uniqueFriendsPosts, ...uniqueUserPosts];
        
        const mergedTotalElements = 
          publicTotalElements + uniqueFriendsPosts.length + uniqueUserPosts.length;
        
        setPosts(mergedContent);
        setTotalPosts(mergedTotalElements);        
      } else {
        if (content) {
          setPosts(content);
        }
        if (totalElements) {
          setTotalPosts(totalElements);
        }
      }
    }  
    } catch (error) {
      console.error("Error fetching posts:", error);
    } finally {
      setLoading(false);
    }
  };
  
  const handlePageChange = (_: React.ChangeEvent<unknown>, page: number) => {
    setCurrentPage(page);
    setLoading(true);
    fetchPosts(page);
  };
  
  useEffect(() => {
    fetchPosts(currentPage);
  }, [currentPage]);
  
  const totalPages = Math.ceil(totalPosts / cardsPerPage);
  
  if (loading) {
    return (
      <Box
        sx={{
          width: "100%",
          height: "50vh",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <CircularProgress />
      </Box>
    );
  }
  
  return (
    <div
    style={{
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      gap: '16px',
      padding: '16px',
    }}
    >
    {/* Render the posts */}
    {posts.length > 0 ? (
      posts.map((post) => (
        <PostCard key={post.id} {...post} />
      ))
    ) : (
      <div>No posts available.</div>
    )}
    
    {/* Pagination controls */}
    <Pagination
    count={totalPages}
    page={currentPage}
    onChange={handlePageChange}
    color="primary"
    style={{ marginTop: '16px' }}
    />
    </div>
  );
};

export default Timeline;
