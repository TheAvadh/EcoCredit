import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import { LoginSignup, ForgetPassword } from "./pages";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login-signup" element={<LoginSignup />} />
        <Route path="/forget-password" element={<ForgetPassword />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
