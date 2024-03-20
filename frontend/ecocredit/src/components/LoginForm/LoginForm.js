import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import { Link, useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import Toast from "../Toast/Toast";
import "./LoginForm.css";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [validated, setValidated] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState("");
  const [toastType, setToastType] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const form = event.currentTarget;

    if (form.checkValidity() === true) {
      const loginData = {
        email,
        password,
      };

      try {
        const response = await fetch(
          `${process.env.REACT_APP_BASE_URL}/auth/signin`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
            },
            body: JSON.stringify(loginData),
          }
        );

        if (!response.ok) throw new Error("Failed to login");

        const data = await response.json();
        console.log("Login Success:", data);
        
        Cookies.set("token", data.token, { secure: true });

        localStorage.setItem("userId", data.userId);
        if (data.role === 'ADMIN') {
          navigate("/admin/scheduled-pickups");
        } else {
          navigate("/role");
        }
      } catch (error) {
        console.error("Login Error:", error);
        setShowToast(true);
        setToastMessage("Wrong credentials");
        setToastType("error");
        setEmail("");
        setPassword("");
      }
    }

    setValidated(true);
  };

  return (
    <div>
      <Form
        className="form-container bg-ec-grey"
        noValidate
        validated={validated}
        onSubmit={handleSubmit}
      >
        <FloatingLabel
          controlId="loginEmail"
          label="Email Address"
          className="mb-3"
        >
          <Form.Control
            type="email"
            name="email"
            placeholder="Email Address"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <Form.Control.Feedback type="invalid">
            Please enter a valid email address.
          </Form.Control.Feedback>
        </FloatingLabel>
        <FloatingLabel
          controlId="loginPassword"
          label="Password"
          className="mb-3"
        >
          <Form.Control
            type="password"
            name="password"
            placeholder="Password"
            minLength={8}
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Form.Control.Feedback type="invalid">
            Please enter a valid password.
          </Form.Control.Feedback>
        </FloatingLabel>
        <div className="d-flex justify-content-end">
          <Link to="/forget-password" className="forgot-password-link">
            Forgot Password?
          </Link>
        </div>
        <Button
          variant="ec-dark-green"
          type="submit"
          className="w-100 mt-4"
          size="lg"
        >
          Login
        </Button>
      </Form>
      <Toast
        showToast={showToast}
        setShowToast={setShowToast}
        toastMessage={toastMessage}
        toastType={toastType}
      />
    </div>
  );
};

export default LoginForm;
