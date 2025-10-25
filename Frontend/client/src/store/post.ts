import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { UserControllerApi, Configuration, PostDTO } from '../api';

export interface postData {
    id: number;
    description: string;
    username: string;
    createdAt: string;
    resultImageId: number;
    originalImageId: number;
    pipelineId: number;
    visibility: number;
}

export interface PostState {
    posts: postData[];
    post: postData | null;
    totalPostsNum: number,
    loading: boolean;
}

const initialState: PostState = {
    posts: [],
    post: null,
    totalPostsNum: 0,
    loading: false,
};

const slice = createSlice({
    name: 'posts',
    initialState,
    reducers: {
        setPosts: (state, action: PayloadAction<postData[]>) => {
            state.posts = action.payload;
            state.loading = false;
        },
        setPost: (state, action: PayloadAction<postData>) => {
            state.post = action.payload;
            state.loading = false;
        },
        setTotalPostsNum: (state, action:PayloadAction<number>) => {
            state.totalPostsNum = action.payload
        },
        setLoading: (state, action: PayloadAction<boolean>) => {
            state.loading = action.payload;
        },
    },
});

const { setPosts, setPost, setTotalPostsNum, setLoading } = slice.actions;

function getAPI(token: string) {
    return new UserControllerApi(new Configuration({
        basePath: "",
        headers: {
            Authorization: token,
        },
        credentials: 'include',
    }));
}

export const actionLoadUserPosts = (token: string, username: string, page: number=0, size: number=5) => async (dispatch: any) => {
    dispatch(setLoading(true));
    
    getAPI(token)
    .getUserPosts({username: username, page: page, size: size })
    .then(data => {
        if (data.content) {
            dispatch(
                setPosts(
                    data.content.map((post: PostDTO) => ({
                        id: post.id,
                        description: post.description,
                        username: post.username,
                        createdAt: new Date(post.createdAt).toUTCString(),
                        resultImageId: post.resultImageId,
                        originalImageId: post.originalImageId,
                        pipelineId: post.pipelineId,
                        visibility: post.visibility,
                    }))
                )
            );
        } else {
            dispatch(setPosts([]));
        }
        dispatch(setTotalPostsNum(data.totalElements || 0));
    });
};

export const actionLoadPostById = (token: string, id: number) => async (dispatch: any) => {
    dispatch(setLoading(true));
    
    try {
        const api = getAPI(token);
        
        const response = await api.getPostById({ id });
        const post = {
            id: response.id,
            description: response.description,
            username: response.username,
            createdAt: new Date(response.createdAt).toUTCString(),
            resultImageId: response.resultImageId,
            originalImageId: response.originalImageId,
            pipelineId: response.pipelineId,
            visibility: response.visibility,
        };
        
        dispatch(setPost(post));
    } catch (error) {
        console.error("Error loading post by ID:", error);
        dispatch(setLoading(false));
    }
};

export default slice.reducer;
