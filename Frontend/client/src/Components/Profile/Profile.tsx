import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Box, CircularProgress, Typography, Avatar, List, ListItem, ListItemAvatar, ListItemText, Divider } from "@mui/material";
import { Link } from "react-router-dom";
import { actionLoadUsers } from "../../store/users";
import { actionLoadUserPipelines } from "../../store/pipelines";
import { actionLoadUserPosts } from "../../store/post";
import { GlobalState } from "../../store";
import { AppDispatch } from "../../store";
import "./Profile.css";

export const Profile = () => {
  const dispatch = useDispatch<AppDispatch>();
  const username = useSelector((state: GlobalState) => state.session.username);
  const token = useSelector((state: GlobalState) => state.session.token);

  const user = useSelector((state: GlobalState) => state.users.users.find(user => user.username === username));
  const userPipelines = useSelector((state: GlobalState) => state.pipelines.userPipelines);
  const userPosts = useSelector((state: GlobalState) => state.posts.posts);
  const pipelinesLoading = useSelector((state: GlobalState) => state.pipelines.loading);
  const usersLoading = useSelector((state: GlobalState) => state.users.loading);
  const postsLoading = useSelector((state: GlobalState) => state.posts.loading);

  useEffect(() => {
    if (username && token) {
      dispatch(actionLoadUsers(token, username));
      dispatch(actionLoadUserPipelines(token, username));
      dispatch(actionLoadUserPosts(token, username));
    }
  }, [username, token, dispatch]);

  if (usersLoading || pipelinesLoading || postsLoading) {
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

  if (!user) {
    return <Typography>Error: User not found.</Typography>;
  }

  const { displayName, email } = user;
  const pipelines = userPipelines.map((pipeline) => `${pipeline.uploadedBy}'s Pipeline ${pipeline.id}`);
  const posts = userPosts.map(post => `${post.username}'s Post ${post.id}`);

  return (
    <Box className="profile-container">
      <Avatar
        alt={displayName}
        src={"https://preview.redd.it/my-frutiger-aero-ish-profile-pic-practice-v0-hqkf9wk38amc1.png?auto=webp&s=9ff06d6960fd78f3098b4a92737cf72b9f7a0ad9"}
        style={{
          width: "100px",
          height: "100px",
          margin: "0 auto",
        }}
      />
      <Typography variant="h5" fontWeight="bold">
        {displayName}
      </Typography>
      <Typography variant="subtitle1" color="textSecondary">
        @{username}
      </Typography>
      <Typography variant="body2" color="textSecondary" className="profile-info">
        {email}
      </Typography>
      <Typography variant="body2" color="textSecondary">⠀</Typography>
      <Divider className="divider" />

      <Typography variant="body2" color="textSecondary">⠀</Typography>
      <Typography variant="h6" fontWeight="bold" sx={{ marginBottom: 2 }}>
        Pipelines
      </Typography>
      <Box className="pipelines-section">
                  {pipelines.length > 0 ? (
                    <List>
                      {pipelines.map((pipeline, index) => (
                        <ListItem key={index}>
                          <ListItemAvatar>
                            <Avatar>{pipeline[0]}</Avatar>
                          </ListItemAvatar>
                          <Link to={`/pipeline/${pipeline.split(" ")[2]}`}>
                            <ListItemText primary={pipeline} />
                          </Link>
                        </ListItem>
                      ))}
                    </List>
                  ) : (
                    <Typography variant="body2" color="textSecondary">
                      No pipelines available.
                    </Typography>
                  )}
          </Box>
      <Divider className="divider" />

      <Typography variant="body2" color="textSecondary">⠀</Typography>
      <Typography variant="h6" fontWeight="bold" sx={{ marginBottom: 2 }}>
        Posts
      </Typography>
      <Box className="posts-section">
        {posts.length > 0 ? (
          <List>
            {posts.map((post, index) => (
              <ListItem key={index}>
                <ListItemAvatar>
                  <Avatar>{post[0]}</Avatar>
                </ListItemAvatar>
                <Link to={`/post/${post.split(" ")[2]}`}>
                  <ListItemText primary={post} />
                </Link>
              </ListItem>
            ))}
          </List>
        ) : (
          <Typography variant="body2" color="textSecondary">
            No posts available.
          </Typography>
        )}
      </Box>
    </Box>
  );
};

export default Profile;
