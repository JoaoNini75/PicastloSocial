import { Box, CircularProgress, Divider, Typography } from "@mui/material";
import { Link, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { actionLoadPostById } from "../../store/post";
import { AppDispatch, GlobalState } from "../../store";
import { useEffect } from "react";


const Post: React.FC = () => {
  const { id } = useParams();
  const dispatch = useDispatch<AppDispatch>();
  
  const token = useSelector((state: GlobalState) => state.session.token);
  const postData = useSelector((state: GlobalState) => state.posts.post);
  const loading = useSelector((state: GlobalState) => state.posts.loading);
  
  useEffect(() => {
    if (token && id) {
      dispatch(actionLoadPostById(token, Number(id)));
    }
  }, [token, id, dispatch]);

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

  if (!postData) {
    return <Typography>Error: Post not found.</Typography>;
  }

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
      <Box
        sx={{
          width: "100%",
          maxWidth: 600,
          padding: 2,
          border: "1px solid #ddd",
          borderRadius: 2,
          backgroundColor: "#f9f9f9",
        }}
      >
        <Typography variant="h6" fontWeight="bold">
          Post {id}
        </Typography>
        <Divider sx={{ marginBottom: 2 }} />

        <Typography variant="body2" color="textSecondary">
          <strong>Uploaded By: </strong> {postData.username}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          <strong>Description: </strong> {postData.description}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          <strong>Result Image Id: </strong> {postData.resultImageId}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          <strong>Original Image Id: </strong> {postData.originalImageId}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          <strong>Pipeline Id: </strong> {postData.pipelineId}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          <strong>Visibility: </strong> {postData.visibility}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          <strong>Created At: </strong> {new Date(postData.createdAt).toLocaleString()}
        </Typography>

        <Divider sx={{ marginY: 2 }} />
      </Box>
    </Box>
  );
}

export default Post;
