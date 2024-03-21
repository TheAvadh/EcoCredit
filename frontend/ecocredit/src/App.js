import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import {
  LoginSignup,
  ForgetPassword,
  ResetPassword,
  RoleSelection,
  CustomerDashboard,
  RecyclerDashboard,
  ProfilePage,
  AdminDashboard,
} from "./pages";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login-signup" element={<LoginSignup />} />
        <Route path="/forget-password" element={<ForgetPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/role" element={<RoleSelection />} />
        <Route path="/customer/*" element={<CustomerDashboard />} />
        <Route path="/recycler/*" element={<RecyclerDashboard />} />
        <Route path="/profile-page" element={<ProfilePage />} />
        <Route path="/admin/*" element={<AdminDashboard />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
