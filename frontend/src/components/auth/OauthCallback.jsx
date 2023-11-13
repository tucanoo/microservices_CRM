import React from 'react';
import {useParams} from "react-router";

const OauthCallback = () => {
  const params = useParams();
  console.log(params)
  return (
    <div>
      <h1>Callback</h1>
    </div>
  );
};

export default OauthCallback;