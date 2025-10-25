import { Card, CardContent, CardHeader, Avatar, Typography, Grid } from '@mui/material';
import { Link } from 'react-router-dom';

export interface PostInterface {
    id: number,
    username: string,
    date: string,
    description: string,
    imageUrl: string,
    pipelineId: string
}

export const PostCard = (post : PostInterface) => {
    return (
        <Card key={post.id} style={{ maxWidth: '800px', width: '100%' }}>
            <CardHeader
              avatar={<Avatar>{post.id}</Avatar>}
              title={post.username}
              subheader={post.date}
            />
            <CardContent>
              <Grid container spacing={2} alignItems="start">
                {/* Image on the Left */}
                <Grid item xs={4}>
                  <img
                    src={post.imageUrl}
                    alt={`Image ${post.id}`}
                    style={{ width: '100%', borderRadius: '8px' }}
                  />
                </Grid>
  
                {/* Content on the Right */}
                <Grid
                  item
                  xs={8}
                  style={{
                    position: 'relative',
                  }}
                >
                  {/* Link */}
                  <Link to={`/pipeline/${post.pipelineId}`}>
                    Load pipeline in GUI
                  </Link>
  
                  {/* Description */}
                  <Typography
                    variant="body1"
                    style={{
                      marginTop: '24px',
                    }}
                  >
                    {post.description}
                  </Typography>
                </Grid>
              </Grid>
            </CardContent>
        </Card>
    );
};
  
export default PostCard;
