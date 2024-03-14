import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import FloatingLabel from "react-bootstrap/FloatingLabel";

const SignupForm = () => {
  const [validated, setValidated] = useState(false);
  const [signupData, setSignupData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    address: {
      street: "",
      city: "",
      province: "",
      postalCode: "",
      country: "Canada",
    },
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (Object.keys(signupData.address).includes(name)) {
      setSignupData({
        ...signupData,
        address: { ...signupData.address, [name]: value },
      });
    } else {
      setSignupData({
        ...signupData,
        [name]: value,
      });
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const form = event.currentTarget;

    if (form.checkValidity() === true) {
      try {
        const response = await fetch(
          `${process.env.REACT_APP_BASE_URL}/auth/signup`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
            },
            body: JSON.stringify(signupData),
          }
        );

        if (!response.ok) throw new Error("Failed to signup");

        const data = await response.json();
        console.log("Signup Success:", data);
        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.userId);
      } catch (error) {
        console.error("Signup Error:", error);
      }
    } else {
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
            <Form.Control
              type="text"
              name="firstName"
              placeholder="First Name"
              required
              value={signupData.firstName}
              onChange={handleInputChange}
            />
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
            <Form.Control
              type="text"
              name="lastName"
              placeholder="Last Name"
              required
              value={signupData.lastName}
              onChange={handleInputChange}
            />
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
        <Form.Control
          type="email"
          name="email"
          placeholder="Email Address"
          required
          value={signupData.email}
          onChange={handleInputChange}
        />
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
          name="password"
          placeholder="Password"
          minLength={8}
          required
          value={signupData.password}
          onChange={handleInputChange}
        />
        <Form.Control.Feedback type="invalid">
          Please enter a password that is at least 8 characters long.
        </Form.Control.Feedback>
      </FloatingLabel>
      <FloatingLabel
        controlId="signupStreetAddress"
        label="Street Address"
        className="mb-3"
      >
        <Form.Control
          type="text"
          name="street"
          placeholder="Street Address"
          required
          value={signupData.address.street}
          onChange={handleInputChange}
        />
        <Form.Control.Feedback type="invalid">
          Please enter your street address.
        </Form.Control.Feedback>
      </FloatingLabel>
      <Row className="g-2">
        <Col xl>
          <FloatingLabel controlId="signupCity" label="City">
            <Form.Control
              type="text"
              name="city"
              placeholder="City"
              required
              value={signupData.address.city}
              onChange={handleInputChange}
            />
            <Form.Control.Feedback type="invalid">
              Please enter your city.
            </Form.Control.Feedback>
          </FloatingLabel>
        </Col>
        <Col xl>
          <FloatingLabel controlId="signupProvince" label="Province">
            <Form.Select
              name="province"
              aria-label="Province"
              required
              value={signupData.address.province}
              onChange={handleInputChange}
            >
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
            <Form.Control
              type="text"
              name="postalCode"
              placeholder="Postal Code"
              required
              value={signupData.address.postalCode}
              onChange={handleInputChange}
            />
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
