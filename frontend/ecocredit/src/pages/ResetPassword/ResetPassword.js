import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import logoUrl from "../../assets/images/logo.png";

function ResetPassword() {
  return (
    <Container className="d-flex vh-100 justify-content-center align-items-center bg-ec-light-green">
      <Row className="w-100">
        <Col lg={6} className="mx-auto">
          <div className="mb-4 text-center">
            <img src={logoUrl} alt="EcoCredit Logo" className="logo" />
            <h1 className="title">EcoCredit</h1>
          </div>
          <Form className="form-container bg-ec-grey">
            <h2 className="text-center text-ec-dark-green mb-4">
              Reset Your Password
            </h2>
            <FloatingLabel
              controlId="email"
              label="Email Address"
              className="mb-3"
            >
              <Form.Control type="email" placeholder="Email Address" required />
            </FloatingLabel>
            <FloatingLabel
              controlId="password"
              label="Password"
              className="mb-3"
            >
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
        </Col>
      </Row>
    </Container>
  );
}

export default ResetPassword;
