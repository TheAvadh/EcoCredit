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
  PaymentFailurePage,
} from "./pages";

import PickupSchedule from "./pages/PickupSchedule/PickupSchedule";
import SuccessPage from "./pages/success/success";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginSignup />} />
        <Route path="/forget-password" element={<ForgetPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/pickup-schedule" element={<PickupSchedule />} />
        <Route path="/role" element={<RoleSelection />} />
        <Route path="/customer/*" element={<CustomerDashboard />} />
        <Route path="/recycler/*" element={<RecyclerDashboard />} />
        <Route path="/profile-page" element={<ProfilePage />} />
        <Route path="/admin/*" element={<AdminDashboard />} />
        <Route path="/success" element={<SuccessPage />} />
        <Route path="/failure" element={<PaymentFailurePage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
