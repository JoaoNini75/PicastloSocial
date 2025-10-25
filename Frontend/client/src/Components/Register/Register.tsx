import { useState } from "react";
//import { Configuration } from "../../api";
import { Box, Button, TextField, Typography } from "@mui/material";

// const config = new Configuration({
//   headers: {
//     "Content-Type": "application/json",
//   },
// });

// async function handleRegister() {
//      Register request
// }

export const Register = () => {
  const [username, setUsername] = useState("");
  const [displayName, setDisplayName] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [role] = useState("USER");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setSuccess("");

    try {
      const user = {
        username,
        display_name: displayName,
        password_hash: password,
        email,
        role,
      };

      // const response = await api.registerUser(user);
      setSuccess("Registration successful!");
    } catch (err: any) {
      setError(err.message || "Registration failed.");
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
        Sociastlo Register
      </Typography>
      <form onSubmit={handleRegister}>
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
          label="Display Name"
          variant="outlined"
          fullWidth
          margin="normal"
          value={displayName}
          onChange={(e) => setDisplayName(e.target.value)}
          required
        />
        <TextField
          label="Email"
          type="email"
          variant="outlined"
          fullWidth
          margin="normal"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
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
          {loading ? "Registering..." : "Register"}
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

export default Register;
