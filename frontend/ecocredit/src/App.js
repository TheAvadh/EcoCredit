import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import { LoginSignup } from "./pages";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login-signup" element={<LoginSignup />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
