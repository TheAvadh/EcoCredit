import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import { Link } from "react-router-dom";
import "./LoginForm.css";

const LoginForm = () => {
  const [validated, setValidated] = useState(false);

  const handleSubmit = (event) => {
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    }

    setValidated(true);
  };

  return (
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
        <Form.Control type="email" placeholder="Email Address" required />
        <Form.Control.Feedback type="invalid">
          Please enter a valid email address.
        </Form.Control.Feedback>
      </FloatingLabel>
      <FloatingLabel
        controlId="loginPassword"
        label="Password"
        className="mb-3"
      >
        <Form.Control type="password" placeholder="Password" required />
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
  );
};

export default LoginForm;
