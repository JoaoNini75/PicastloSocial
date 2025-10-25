import Timeline from '../Timeline/Timeline';
import GroupHeader from './GroupHeader';
import { useParams } from 'react-router-dom';

const Group = () => {
    const { id } = useParams<{ id: string }>(); 

    if (!id)
        return <h2>Something went wrong...</h2>
    
    const idNumber = parseInt(id);

    return (
        <>
            <div style={{ margin: '20px 0' }}>
                <GroupHeader id={idNumber} />
            </div>
            <Timeline type={"group"} groupId={idNumber} />
        </>
    );
};

export default Group;