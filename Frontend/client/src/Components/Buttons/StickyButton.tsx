import { Button } from '@mui/material';

export interface StickyButtonInterface {
	title: string
}

const StickyButton = ({ title }: StickyButtonInterface) => {
  return (
    <div>
      <Button
        variant="contained"
        color="primary"
        style={{
          backgroundColor: '#123123',
          position: 'fixed',
          bottom: '16px',
          right: '16px',
          zIndex: 1000, // Ensures it's above other content
        }}
      >
        {title}
      </Button>
    </div>
  );
};

export default StickyButton;
