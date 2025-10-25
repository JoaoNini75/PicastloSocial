import { Foldable } from "./Utils";
import { PipelineExporter, OBJExporter, PipelineLoader } from "./Exporters";
import { PicastLoImage } from "../model/Image";
import { TransformationPipeline } from "../model/Transformation";

interface ExportersInterface { 
    onSelect: () => void, 
    image:PicastLoImage|null,
    pipeline:TransformationPipeline,
    onLoad: (selected:number) => void,
    transformation: string | undefined
}

let transformationLoaded = false;

const ImageTools = ({ onSelect, image, pipeline, onLoad, transformation }:ExportersInterface) => {

    if (transformation && !transformationLoaded) {
        try {
          const parsedTransformation = JSON.stringify(JSON.parse(transformation));
          pipeline.fromJSON(parsedTransformation);
          onLoad(pipeline.length());
          transformationLoaded = true;
        } catch (error: any) {
          console.log("Error loading transformation:", error);
        }
    }
    
    return (
        <div className="widget-card" onClick={onSelect}>
        <Foldable title={`Final Image`}>
        <div><OBJExporter outputImage={image} /></div>
        <div>
        <PipelineExporter pipeline={pipeline} />
        <PipelineLoader pipeline={pipeline} onLoad={onLoad}/>
        </div>
        </Foldable>
        </div>
    )
}

export default ImageTools