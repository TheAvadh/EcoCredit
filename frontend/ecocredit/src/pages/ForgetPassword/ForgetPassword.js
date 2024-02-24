import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import FormContainer from "../../components/FormContainer";

const ForgetPassword = () => {
  const [email, setEmail] = useState("");
  const [validated, setValidated] = useState(false);

  const handleSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;

    if (form.checkValidity() === true) {
      const formData = {
        email,
      };
      const jsonPayload = JSON.stringify(formData);
      console.log(jsonPayload);
      fetch(`${process.env.REACT_APP_BASE_URL}/auth/forget-password`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: jsonPayload,
      })
        .then((response) => response.json())
        .then((data) => {
          console.log("Success:", data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    } else {
      event.stopPropagation();
    }

    setValidated(true);
  };

  return (
    <FormContainer title="Reset Your Password">
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        <FloatingLabel controlId="email" label="Email Address">
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
