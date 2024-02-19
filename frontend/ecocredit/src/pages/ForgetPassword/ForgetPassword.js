import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import FormContainer from "../../components/FormContainer";

const ForgetPassword = () => {
  return (
    <FormContainer title="Reset Your Password">
      <Form>
        <FloatingLabel controlId="email" label="Email Address">
          <Form.Control type="email" placeholder="Email Address" required />
        </FloatingLabel>
        <div className="d-grid">
          <Button
            variant="ec-dark-green"
            type="submit"
            className="w-50 mx-auto mt-4"
            size="lg"
          >
            Send Reset Link
          </Button>
        </div>
      </Form>
    </FormContainer>
  );
};

export default ForgetPassword;
