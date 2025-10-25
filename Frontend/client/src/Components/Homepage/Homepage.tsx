import StickyButton from '../Buttons/StickyButton';
import Timeline from '../Timeline/Timeline';

const Homepage = () => {
    return (
        <>
            <Timeline type={"all"} groupId={undefined} />
            <StickyButton title="Create Post" />
        </>
    );
};

export default Homepage;
