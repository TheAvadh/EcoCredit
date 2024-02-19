import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import FloatingLabel from "react-bootstrap/FloatingLabel";

const SignupForm = () => {
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
      <Row className="g-2">
        <Col md>
          <FloatingLabel
            controlId="signupFirstName"
            label="First Name"
            className="mb-3"
          >
            <Form.Control type="text" placeholder="First Name" required />
            <Form.Control.Feedback type="invalid">
              Please enter your first name.
            </Form.Control.Feedback>
          </FloatingLabel>
        </Col>
        <Col md>
          <FloatingLabel
            controlId="signupLastName"
            label="Last Name"
            className="mb-3"
          >
            <Form.Control type="text" placeholder="Last Name" required />
            <Form.Control.Feedback type="invalid">
              Please enter your last name.
            </Form.Control.Feedback>
          </FloatingLabel>
        </Col>
      </Row>
      <FloatingLabel
        controlId="signupEmail"
        label="Email Address"
        className="mb-3"
      >
        <Form.Control type="email" placeholder="Email Address" required />
        <Form.Control.Feedback type="invalid">
          Please enter a valid email address.
        </Form.Control.Feedback>
      </FloatingLabel>
      <FloatingLabel
        controlId="signupPassword"
        label="Password"
        className="mb-3"
      >
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
      <FloatingLabel controlId="signupAddress" label="Address" className="mb-3">
        <Form.Control type="text" placeholder="Address" required />
        <Form.Control.Feedback type="invalid">
          Please enter your address.
        </Form.Control.Feedback>
      </FloatingLabel>
      <Row className="g-2">
        <Col xl>
          <FloatingLabel controlId="signupCity" label="City">
            <Form.Control type="text" placeholder="City" required />
            <Form.Control.Feedback type="invalid">
              Please enter your city.
            </Form.Control.Feedback>
          </FloatingLabel>
        </Col>
        <Col xl>
          <FloatingLabel controlId="signupProvince" label="Province">
            <Form.Select aria-label="Province" required>
              <option value="">Select a province</option>
              <option value="Alberta">Alberta</option>
              <option value="British Columbia">British Columbia</option>
              <option value="Manitoba">Manitoba</option>
              <option value="New Brunswick">New Brunswick</option>
              <option value="Newfoundland and Labrador">
                Newfoundland and Labrador
              </option>
              <option value="Nova Scotia">Nova Scotia</option>
              <option value="Ontario">Ontario</option>
              <option value="Prince Edward Island">Prince Edward Island</option>
              <option value="Quebec">Quebec</option>
              <option value="Saskatchewan">Saskatchewan</option>
            </Form.Select>
            <Form.Control.Feedback type="invalid">
              Please select your province.
            </Form.Control.Feedback>
          </FloatingLabel>
        </Col>
        <Col xl>
          <FloatingLabel controlId="signupPostalCode" label="Postal Code">
            <Form.Control type="text" placeholder="Postal Code" required />
            <Form.Control.Feedback type="invalid">
              Please select your postal code.
            </Form.Control.Feedback>
          </FloatingLabel>
        </Col>
      </Row>
      <Button
        variant="ec-dark-green"
        type="submit"
        className="w-100 mt-4"
        size="lg"
      >
        Signup
      </Button>
    </Form>
  );
};

export default SignupForm;
