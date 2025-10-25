import { Card, CardHeader, Avatar } from '@mui/material';
import { Link } from 'react-router-dom';
import '../Users/ClickableCard.css';
import { GroupMembershipDTO } from '../../api';

export const GroupCard = ({groupId, name, createdAt}: GroupMembershipDTO) => {
    const groupIdStr = groupId.toString();

    return (
        <Link to={`/group/${groupIdStr}`} style={{
                maxWidth: '600px',
                width: '100%',
                textDecoration: 'none' }}>

            <Card className="clickable-card">
                <CardHeader
                    avatar={<Avatar>Pfp</Avatar>}
                    title={name}
                    subheader={"Created at " + createdAt}
                />
            </Card>
        </Link>
    );
};

export default GroupCard;

