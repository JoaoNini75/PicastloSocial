import { Card, CardHeader, Avatar } from '@mui/material';
import { Link } from 'react-router-dom';
import './ClickableCard.css';

export interface UserCardInterface {
    username: string,
    displayName: string
}

export const UserCard = (user: UserCardInterface) => {
    return (
        <Link to={`/user/${user.username}`} style={{
            maxWidth: '400px',
            width: '100%',
            textDecoration: 'none' }}>

            <Card className="clickable-card">
                <CardHeader
                    avatar={<Avatar>Pfp</Avatar>}
                    title={user.displayName}
                    subheader={user.username}
                />
            </Card>
        </Link>
    );
};

export default UserCard;

