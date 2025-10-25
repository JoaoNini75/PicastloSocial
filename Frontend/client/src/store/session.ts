import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { UserControllerApi, Configuration } from '../api';

export interface SessionState {
    token: string,
    username: string,
    loggedIn: boolean
}

interface LoginPayload {
    token: string;
    username: string;
}

const config = new Configuration({
    headers: {
      "Authorization": ""
    },
});

const initialState: SessionState = { token: "", username: "", loggedIn: false }

const slice = createSlice({
    name: 'session',
    initialState,
    reducers: {
        loginUser: (state, action:PayloadAction<LoginPayload>) => {
            state.token = action.payload.token;
            state.username = action.payload.username;
            state.loggedIn = true;
        }
    },
});

const { loginUser } = slice.actions

export const actionLoginUser = (token: string, username: string) => async (dispatch: any) => {
    dispatch(loginUser({token, username}));   
}

export default slice.reducer
