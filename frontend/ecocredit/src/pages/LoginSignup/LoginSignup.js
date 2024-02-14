import React, { useState } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import LoginForm from "../../components/LoginForm";
import SignupForm from "../../components/SignupForm";
import "./LoginSignup.css";

const LoginSignup = () => {
  const [activeView, setActiveView] = useState("login");

  return (
    <Container className="d-flex vh-100 justify-content-center align-items-center">
      <Row className="w-100">
        <Col md={6} className="mx-auto">
          <div className="tabs-container">
            <Button
              variant={activeView === "login" ? "primary" : "secondary"}
              onClick={() => setActiveView("login")}
              className="tab-button"
            >
              Login
            </Button>
            <Button
              variant={activeView === "signup" ? "primary" : "secondary"}
              onClick={() => setActiveView("signup")}
              className="tab-button"
            >
              Signup
            </Button>
          </div>

          {activeView === "login" ? (
            <LoginForm onLogin={() => {}} />
          ) : (
            <SignupForm onSignup={() => {}} />
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default LoginSignup;
