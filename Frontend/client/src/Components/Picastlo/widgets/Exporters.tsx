import { useRef, useState, useEffect } from 'react';
import { PicastLoImage } from '../model/Image';
import { Renderer, TransformationPipeline } from '../model/Transformation'

import { UserControllerApi, Configuration } from "../../../api";
import { GlobalState } from '../../../store';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';


function save(filename: string, data: BlobPart) {
    const blob = new Blob([data], { type: 'text/csv' });
    const elem = window.document.createElement('a');
    elem.href = window.URL.createObjectURL(blob);
    elem.download = filename;
    document.body.appendChild(elem);
    elem.click();
    document.body.removeChild(elem);
}

export const OBJExporter = ({ outputImage }: { outputImage: PicastLoImage | null }) => {
    
    const saveObj = () => {
        if (!outputImage) return;
        let renderer = new Renderer(outputImage, outputImage, 1);
        let raw: string = renderer.render();
        save('output.obj', raw);
    }
    
    return <div>
    <button className="button button-dark" onClick={saveObj}>Export to OBJ</button>
    </div>
}

async function send(image: string): Promise<string> {
    return fetch("/piscastlo", { method: "POST", body: btoa(image) })
    .then(req => req.json())
}

export const BlenderExporter = ({ outputImage }: { outputImage: PicastLoImage | null }) => {
    
    const saveObj = () => {
        if (!outputImage) return;
        let renderer = new Renderer(outputImage, outputImage, 2);
        let raw: string = renderer.render();
        send(raw).then((data: string) => { save('output.stl', atob(data)) });
    }
    
    return <div>
    <button className="button button-dark" onClick={saveObj}>Export to STL</button>
    </div>
}

export const SVGExporter = () => <div></div>


function saveJSON(data: string, filename: string = 'data.json') {
    const blob = new Blob([data], { type: 'text/application-json' });
    const elem = window.document.createElement('a');
    elem.href = window.URL.createObjectURL(blob);
    elem.download = filename;
    document.body.appendChild(elem);
    elem.click();
    document.body.removeChild(elem);
}


export const PipelineExporter = ({ pipeline }: { pipeline: TransformationPipeline }) => {
    const loggedIn = useSelector((state:GlobalState) => state.session.loggedIn);
    const username = useSelector((state: GlobalState) => state.session.username);
    const navigate = useNavigate();
    
    const saveObj = () => {
        save('pipeline.json', pipeline.toJSON());
    }
    
    const uploadToProfile = async () => {
        try {
            const api = new UserControllerApi(new Configuration({
                basePath: "",
                credentials: "include",
            }));
    
            const encoder = new TextEncoder();

            const dataToUpload = encoder.encode(pipeline.toJSON());
            
            const byteArray = new Uint8Array(dataToUpload);
    
            const base64Data = btoa(String.fromCharCode.apply(null, Array.from(byteArray)));
    
            const pipelineToUpload = {
                pipelineData: base64Data,
                uploadedBy: username,
            };
    
            const response = await api.createPipeline({ createPipeDTO: pipelineToUpload });
            console.log('Pipeline uploaded:', response);
            
            navigate('/profile');
        } catch (error) {
            console.error("Error uploading pipeline data", error);
        }
    }
    
    
    return (<><button className="button button-dark" onClick={saveObj}>Save</button><br></br>
        {loggedIn && 
            (<><button className="button button-dark" onClick={uploadToProfile}>Upload To Profile</button><br></br></>)
        }
        </>);
    }
    
    interface PipelineLoaderInterface {
        pipeline: TransformationPipeline,
        onLoad: (selected:number) => void
    }
    
    export const PipelineLoader = ({pipeline, onLoad}:PipelineLoaderInterface) => {
        
        const [error, setError] = useState<string | null>(null)
        
        const inputRef = useRef<HTMLInputElement>(null)
        const clickButton = () => {
            inputRef.current?.click()
        }
        
        const loadFile = (event: React.ChangeEvent<HTMLInputElement>) => {
            const file = event.target.files?.[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = (e: ProgressEvent<FileReader>) => {
                    try{
                        pipeline.fromJSON(e.target?.result as string)
                        onLoad(pipeline.length())
                    } catch(error:any) {
                        setError(error.message)
                    }
                };
                reader.readAsText(file);
            }
        };
        
        
        return <>
        <button className="button button-dark" onClick={clickButton}>Load</button>
        <input ref={inputRef} style={{display:"none"}} accept=".json" type="file" onChange={loadFile}/>
        {error && <p style={{color:'red'}}>{error}<span style={{ cursor: 'grab', height:'0px', position:'relative',top:'0px',left:'300px'}} onClick={()=>setError(null)}>x</span></p>}
        </>
    }