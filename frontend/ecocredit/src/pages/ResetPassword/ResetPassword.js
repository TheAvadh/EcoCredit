import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import FormContainer from "../../components/FormContainer";

const ResetPassword = () => {
  return (
    <FormContainer title="Reset Your Password">
      <Form>
        <FloatingLabel controlId="email" label="Email Address" className="mb-3">
          <Form.Control type="email" placeholder="Email Address" required />
        </FloatingLabel>
        <FloatingLabel controlId="password" label="Password" className="mb-3">
          <Form.Control
            type="password"
            placeholder="Password"
            minLength={8}
            required
          />
        </FloatingLabel>
        <FloatingLabel controlId="confirmPassword" label="Confirm Password">
          <Form.Control
            type="password"
            placeholder="Confirm Password"
            minLength={8}
            required
          />
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
