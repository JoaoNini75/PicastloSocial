import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { UserControllerApi, Configuration, PipelineDTO } from '../api';

export interface pipelineData {
    id: number;
    pipelineData:string;
    uploadedBy:string; 
    createdAt:string;
}

export interface PipelineState {
    userPipelines: pipelineData[],
    pipeline: pipelineData,
    totalPipelineNum: number,
    loading: boolean,
    uploading: boolean
}

const mockPipeline = {id: 0, pipelineData:"", uploadedBy:"", createdAt:""};

const initialState: PipelineState = { userPipelines: [], pipeline:mockPipeline , totalPipelineNum: 0, loading: false, uploading: false }

const slice = createSlice({
    name: 'pipelines',
    initialState,
    reducers: {
        setUserPipelines: (state, action:PayloadAction<pipelineData[]>) => {
            state.userPipelines = action.payload
            state.loading = false
        },
        setPipeline: (state, action: PayloadAction<pipelineData>) => {
            state.pipeline = action.payload
            state.loading = false
        },
        setTotalPipelinesNum: (state, action:PayloadAction<number>) => {
            state.totalPipelineNum = action.payload
        },
        setLoading: (state, action:PayloadAction<boolean>) => {
            state.loading = action.payload
        },
        setUploading: (state, action:PayloadAction<boolean>) => {
            state.uploading = action.payload
        }
    },
});

const { setUserPipelines, setPipeline, setTotalPipelinesNum, setLoading } = slice.actions

function getAPI(token: string) {
    return new UserControllerApi(new Configuration({
        basePath: "",
        headers: {
            Authorization: token,
        },
        credentials: "include"
}))};
  
export const actionLoadUserPipelines = (token: string, username: string, page: number=0, size: number=5) => async (dispatch: any) => {
    dispatch(setLoading(true))
    getAPI(token).getPipelinesByUser({username: username, page: page, size: size})
    .then(data => { 
        if (data.content)
            dispatch(setUserPipelines(
                data.content.map((pipeline: PipelineDTO) => ({
                  id: pipeline.id,
                  pipelineData: pipeline.pipelineData, 
                  uploadedBy: pipeline.uploadedBy,
                  createdAt: new Date(pipeline.createdAt).toUTCString(),
                }))
              ));
        else 
            dispatch(setUserPipelines([])); // Ensure the users list is cleared for no results

        dispatch(setTotalPipelinesNum(data.totalElements || 0));
    })    
}

export const actionLoadPipeline = (token: string, id: number) => async (dispatch: any) => {
    dispatch(setLoading(true));
  
    getAPI(token)
      .getPipelineById({ id: id })
      .then(data => {
        if (data) {
          const pipelinedata = data;
  
          const pipeline = {
            id: pipelinedata.id,
            pipelineData: pipelinedata.pipelineData,
            uploadedBy: pipelinedata.uploadedBy,
            createdAt: new Date(pipelinedata.createdAt).toUTCString(),
          };
  
          //console.log(pipeline);
          dispatch(setPipeline(pipeline));
        }
      });
  };

export default slice.reducer
