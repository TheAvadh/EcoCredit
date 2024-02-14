import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";

const LoginForm = ({ onLogin }) => {
  return (
    <Form className="form-container bg-ec-grey" onSubmit={onLogin}>
      <FloatingLabel
        controlId="loginEmail"
        label="Email Address"
        className="mb-3"
      >
        <Form.Control type="email" placeholder="Email Address" required />
      </FloatingLabel>
      <FloatingLabel controlId="loginPassword" label="Password">
        <Form.Control type="password" placeholder="Password" required />
      </FloatingLabel>
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
