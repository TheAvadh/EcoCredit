import React, { useState } from "react";
import {useSearchParams} from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import FormContainer from "../../components/FormContainer";

const ResetPassword = () => {
  const [email, setEmail] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newPasswordRepeat, setNewPasswordRepeat] = useState("");
  const [validated, setValidated] = useState(false);
  const [passwordsMatch, setPasswordsMatch] = useState(true);
  const [searchParams,] = useSearchParams();

  const handleSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;

    const passwordsDoMatch = newPassword === newPasswordRepeat;
    setPasswordsMatch(passwordsDoMatch);

    if (form.checkValidity() === true && passwordsDoMatch) {
      const formData = {
        email,
        newPassword,
        newPasswordRepeat,
      };

      fetch(`${process.env.REACT_APP_BASE_URL}/auth/reset-password?token=${searchParams.get("token")}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });

      setValidated(false);
    } else {
      event.stopPropagation();
    }

    setValidated(true);
  };

  return (
    <FormContainer title="Reset Your Password">
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        <FloatingLabel controlId="email" label="Email Address" className="mb-3">
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
        <FloatingLabel controlId="password" label="Password" className="mb-3">
          <Form.Control
            type="password"
            name="newPassword"
            placeholder="Password"
            minLength={8}
            required
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />
          <Form.Control.Feedback type="invalid">
            Please enter a password that is at least 8 characters long.
          </Form.Control.Feedback>
        </FloatingLabel>
        <FloatingLabel controlId="confirmPassword" label="Confirm Password">
          <Form.Control
            type="password"
            name="newPasswordRepeat"
            placeholder="Confirm Password"
            minLength={8}
            required
            value={newPasswordRepeat}
            onChange={(e) => setNewPasswordRepeat(e.target.value)}
            isInvalid={!passwordsMatch}
          />
          {!passwordsMatch && (
            <Form.Control.Feedback type="invalid">
              Passwords do not match.
            </Form.Control.Feedback>
          )}
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
