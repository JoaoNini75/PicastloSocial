import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Button from "@mui/material/Button";
import { Box } from "@mui/material";
import { Link } from 'react-router-dom';
import './Header.css';

export interface HeaderInterface {
    loggedIn: boolean;
}

export const Header = ({ loggedIn } : HeaderInterface) => {
    return (
        <AppBar sx={{bgcolor: '#FFFFFF'}} position="static">
            <Toolbar>

                <Box className="custom-box">
                    <Link to="/" style={{ textDecoration: 'none' }}>
                        <Button variant="contained" className="custom-button" size="large">Homepage</Button>
                    </Link>
                    <Link to="/users" style={{ textDecoration: 'none' }}>
                        <Button variant="contained" className="custom-button" size="large">Users</Button>
                    </Link>
                    <Link to="/groups" style={{ textDecoration: 'none' }}>
                        <Button variant="contained" className="custom-button" size="large">Groups</Button>
                    </Link>
                    <Link to="/picastlo" style={{ textDecoration: 'none' }}>
                        <Button variant="contained" className="custom-button" size="large">Picastlo GUI</Button>
                    </Link>

                    {loggedIn && 
                        (<>
                            <Link to="/profile" style={{ textDecoration: 'none' }}>
                                <Button variant="contained" className="custom-button" size="large">Profile</Button>
                            </Link>
                            <Box
                                component="img"
                                sx={{ height: 40, width: 40, borderRadius: 50 }}
                                alt="pfp"
                                src="https://preview.redd.it/my-frutiger-aero-ish-profile-pic-practice-v0-hqkf9wk38amc1.png?auto=webp&s=9ff06d6960fd78f3098b4a92737cf72b9f7a0ad9"
                            />
                        </>)
                    }

                    {!loggedIn && (
                    <>
                        <Link to="/login" style={{ textDecoration: 'none' }}>
                            <Button variant="contained" className="custom-button" size="large">Login</Button>
                        </Link>
                        {/* <Link to="/register" style={{ textDecoration: 'none' }}>
                            <Button variant="contained" className="custom-button" size="large">Register</Button>
                        </Link> */}
                    </>
                    )}
                </Box>
            </Toolbar>
        </AppBar>
    );
};

export default Header;
