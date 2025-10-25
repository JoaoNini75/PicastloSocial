import { configureStore } from "@reduxjs/toolkit"
//import { logger } from 'redux-logger'
import { GroupDTO, GroupMembershipDTO, ImageDTO } from "../api"
import usersReducer, { UsersState } from "./users"
import postsReducer, { PostState } from "./post"
import sessionReducer, { SessionState } from "./session"
import groupsReducer, { GroupState } from "./groups"
import pipelinesReducer, { PipelineState } from "./pipelines"

export interface GlobalState {
    session: SessionState,
    users: UsersState,
    posts: PostState,
    pipelines: PipelineState,
    images: ImageDTO[],
    groups: GroupState,
    groupMemberships: GroupMembershipDTO[]
}

const store = configureStore({
    reducer : {
        session: sessionReducer,
        users: usersReducer,
        groups: groupsReducer,
        posts: postsReducer,
        pipelines: pipelinesReducer,
        /*images: imagesReducer,
        groupMemberships: groupMembershipsReducer*/
    },
    //middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([logger]),
})

//store.dispatch(actionLoadUsers())

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
