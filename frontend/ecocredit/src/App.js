import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import {
  LoginSignup,
  ForgetPassword,
  ResetPassword,
  PickupSchedule,
  RoleSelection,
  ProfilePage,
} from "./pages";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login-signup" element={<LoginSignup />} />
        <Route path="/forget-password" element={<ForgetPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/pickup-schedule" element={<PickupSchedule />} />
        <Route path="/role" element={<RoleSelection />} />
        <Route path="/profile-page" element={<ProfilePage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
