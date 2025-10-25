import React, { useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import { Box, Typography, Divider, ListItemText, CircularProgress } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, GlobalState } from "../../store";
import { actionLoadPipeline } from "../../store/pipelines";

export const Pipeline = () => {
  const { id } = useParams<{ id: string }>();
  const dispatch = useDispatch<AppDispatch>();

  const token = useSelector((state: GlobalState) => state.session.token);
  const loading = useSelector((state: GlobalState) => state.pipelines.loading);

  useEffect(() => {
    if (token && id) {
      dispatch(actionLoadPipeline(token, Number(id)));
    }
  }, [id, token, dispatch]);

  const pipeline = useSelector((state: GlobalState) => state.pipelines.pipeline);

  if (loading || !pipeline) {
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

  console.log(pipeline);

  const { pipelineData, uploadedBy, createdAt } = pipeline;

  const decodedByteArray = Uint8Array.from(atob(pipelineData), (c) => c.charCodeAt(0));
  const decodedJsonString = new TextDecoder().decode(decodedByteArray);
  

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
          Pipeline {id}
        </Typography>
        <Divider sx={{ marginBottom: 2 }} />

        <Typography variant="body2" color="textSecondary">
          <strong>Uploaded By:</strong> {uploadedBy}
        </Typography>
        <Typography variant="body2" color="textSecondary">
          <strong>Created At:</strong> {new Date(createdAt).toLocaleString()}
        </Typography>

        <Divider sx={{ marginY: 2 }} />

        <Typography variant="body1" sx={{ whiteSpace: "pre-wrap", marginBottom: 1 }}>
          <strong>Pipeline Transformations: </strong>
          <ListItemText primary={decodedJsonString || "Pipeline has no transformations."} />
          <br />
          <Link to={`/picastlo/${decodedJsonString}`}>
            Click Here to load the Pipeline onto Picastlo!
          </Link>
        </Typography>
      </Box>
    </Box>
  );
};

export default Pipeline;
