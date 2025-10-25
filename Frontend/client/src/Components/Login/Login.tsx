import axios from 'axios';
import { useState } from "react";
import { Box, Button, TextField, Typography } from "@mui/material";
import { useNavigate } from 'react-router-dom';
import { useDispatch } from "react-redux"
import { actionLoginUser } from "../../store/session"
import type { AppDispatch } from '../../store';

export const login = async (username: string, password: string): Promise<string> => {
    const response = await axios.post(`/login`, { username, password }, {
        headers: {
            "Content-Type": "application/json",
        },
    });

    return response.headers.authorization;
};

export const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const navigate = useNavigate();
    const dispatch = useDispatch<AppDispatch>();

    const handleLogin = async (event: React.FormEvent) => {
        event.preventDefault();
        setLoading(true);
        setError("");
        setSuccess("");

        try {
            const token = await login(username, password);
            setSuccess("Login successful!");
            
            navigate("/");
            dispatch(actionLoginUser(token, username));

        } catch (err: any) {
            setError(err.response?.data?.message || "An error occurred during login.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box
            sx={{
                maxWidth: 400,
                margin: "0 auto",
                marginTop: 20,
                padding: 4,
                backgroundColor: "#f9f9f9",
                borderRadius: 2,
                boxShadow: 3,
                textAlign: "center",
            }}
        >
            <Typography variant="h5" component="h2" gutterBottom>
                Sociastlo Login
            </Typography>
            <form onSubmit={handleLogin}>
                <TextField
                    label="Username"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <TextField
                    label="Password"
                    type="password"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    size="large"
                    fullWidth
                    sx={{ marginTop: 2, backgroundColor: "#123123" }}
                    disabled={loading}
                >
                    {loading ? "Logging in..." : "Login"}
                </Button>
            </form>
            {error && (
                <Typography variant="body2" color="error" sx={{ marginTop: 2 }}>
                    {error}
                </Typography>
            )}
            {success && (
                <Typography variant="body2" color="success.main" sx={{ marginTop: 2 }}>
                    {success}
                </Typography>
            )}
        </Box>
    );
};

export default Login;
