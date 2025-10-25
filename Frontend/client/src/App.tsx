import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './Components/Header/Header';
import Users from './Components/Users/Users';
import Groups from './Components/Groups/Groups';
import Login from './Components/Login/Login';
import Register from './Components/Register/Register';
import Profile from './Components/Profile/Profile';
import Error from './Components/Error/Error';
import { enable as enableDarkMode } from 'darkreader';
import User from './Components/Users/User';
import Homepage from './Components/Homepage/Homepage';
import Group from './Components/Groups/Group';
import ImageTransformer from './Components/Picastlo/ImageTransformation';
import { Provider, useSelector } from 'react-redux';
import { GlobalState } from './store';
import store from './store';
import Pipeline from './Components/Pipeline/Pipeline';
import Post from './Components/Post/Post';

const App = () => {

  enableDarkMode({
    brightness: 100,
    contrast: 85,
    sepia: 10
  });

  return (
    <Provider store={store}>
      <Router>
        <AppContent/>
      </Router>
    </Provider>
  );
};

const AppContent = () => {
  const loggedIn = useSelector((state:GlobalState) => state.session.loggedIn)

  return (
    <div className="App">
      <Header loggedIn={loggedIn} />

      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/users" element={<Users />} />
        <Route path="/user/:username" element={<User />} />
        <Route path="/groups" element={<Groups />} />
        <Route path="/group/:id" element={<Group />} />
        <Route path="/picastlo/:transformation?" element={<ImageTransformer />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/post/:id" element={<Post />}/>
        <Route path="/pipeline/:id" element={<Pipeline />}/>
        <Route path="/error" element={<Error errorType="404" />} />
        <Route path="/error401" element={<Error errorType="401" />} />
      </Routes>
    </div>
  );
};

export default App;
