import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import FormContainer from "../../components/FormContainer";

const ResetPassword = () => {
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
    <FormContainer title="Reset Your Password">
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        <FloatingLabel controlId="email" label="Email Address" className="mb-3">
          <Form.Control type="email" placeholder="Email Address" required />
          <Form.Control.Feedback type="invalid">
            Please enter a valid email address.
          </Form.Control.Feedback>
        </FloatingLabel>
        <FloatingLabel controlId="password" label="Password" className="mb-3">
          <Form.Control
            type="password"
            placeholder="Password"
            minLength={8}
            required
          />
          <Form.Control.Feedback type="invalid">
            Please enter a password that is at least 8 characters long.
          </Form.Control.Feedback>
        </FloatingLabel>
        <FloatingLabel controlId="confirmPassword" label="Confirm Password">
          <Form.Control
            type="password"
            placeholder="Confirm Password"
            minLength={8}
            required
          />
          <Form.Control.Feedback type="invalid">
            Please enter the same password again for confirmation.
          </Form.Control.Feedback>
        </FloatingLabel>
        <div className="d-grid">
          <Button
            variant="ec-dark-green"
            type="submit"
            className="w-50 mx-auto mt-4"
            size="lg"
          >
            Reset Password
          </Button>
        </div>
      </Form>
    </FormContainer>
  );
};

export default ResetPassword;
