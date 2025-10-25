import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { UserControllerApi, Configuration, GroupDTO, GroupMembershipDTO, GroupMembershipDTOToJSON, GroupDTOToJSON } from '../api';

export interface GroupState {
    groupsMemberships: GroupMembershipDTO[],
    groups: GroupDTO[],
    totalGroupNum: number,
    loading: boolean,
    uploading: boolean
}

const initialState: GroupState = { groupsMemberships: [], groups: [], totalGroupNum: 0, loading: false, uploading: false }

const slice = createSlice({
    name: 'groups',
    initialState,
    reducers: {
        setGroupsMemberships: (state, action:PayloadAction<GroupMembershipDTO[]>) => {
            state.groupsMemberships = action.payload
            state.loading = false
        },
        setGroup: (state, action:PayloadAction<GroupDTO>) => {
            state.groups = [...state.groups, action.payload]
            state.loading = false
        },
        setTotalGroupMembershipsNum: (state, action:PayloadAction<number>) => {
            state.totalGroupNum = action.payload
        },
        setLoading: (state, action:PayloadAction<boolean>) => {
            state.loading = action.payload
        },
        setUploading: (state, action:PayloadAction<boolean>) => {
            state.uploading = action.payload
        }
    },
});

const { setGroupsMemberships, setGroup, setTotalGroupMembershipsNum, setLoading } = slice.actions

function getAPI(token: string) {
    return new UserControllerApi(new Configuration({
        basePath: "",
        headers: {
            Authorization: token,
        },
        credentials: "include"
}))};
  
export const actionLoadUserGroupsMemberships = (token: string, username: string, page: number=0, size: number=5) => async (dispatch: any) => {
    dispatch(setLoading(true))
    getAPI(token).listUserGroups({username: username, page: page, size: size})
    .then(data => { 
        console.log("actionLoadUserGroupsMemberships:");
        console.log(data.content) 
        if (data.content) // map to JSON because of Date object  
            dispatch(setGroupsMemberships(data.content.map((group: GroupMembershipDTO) => GroupMembershipDTOToJSON(group))));
        else 
            dispatch(setGroupsMemberships([])); // Ensure the users list is cleared for no results

        dispatch(setTotalGroupMembershipsNum(data.totalElements || 0));
    })    
}

export const actionLoadGroup = (token: string, id:number) => async (dispatch: any) => {
    dispatch(setLoading(true))
    getAPI(token).getGroupId({id: id})
    .then(data => { 
        if (data) // map to JSON because of Date object 
            dispatch(setGroup(GroupDTOToJSON(data))); 
    })    
}

export default slice.reducer
