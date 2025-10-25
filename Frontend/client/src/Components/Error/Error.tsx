export interface ErrorInterface {
    errorType: string;
}

export const Error = ({ errorType } : ErrorInterface) => {
    if (errorType === "404") {
        return (
            <center>
                <div className="not-found-container">
                    <img
                        src="https://i.imgur.com/T9Xh0Ar.png"
                        alt="Not Found"
                        className="not-found-image"
                    />
                    <h1 className="not-found-text">404</h1>
                    <h2 className="not-found-text">Our minions couldn't find what you were looking for...</h2>
                </div>
            </center>
        );
    } else if (errorType === "401") {
        return (
            <center>
                <div className="not-found-container">
                    <img
                        src="https://i.imgur.com/T9Xh0Ar.png"
                        alt="Not Found"
                        className="not-found-image"
                    />
                    <h1 className="not-found-text">401</h1>
                    <h2 className="not-found-text">Our minions say that you need to be logged in to do that...</h2>
                </div>
            </center>
        );
    } 

    return (<center><h1>Something went wrong...</h1></center>);
}

export default Error;