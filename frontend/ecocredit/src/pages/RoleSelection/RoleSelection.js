import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faRecycle } from "@fortawesome/free-solid-svg-icons";
import logoUrl from "../../assets/images/logo.png";
import "./RoleSelection.css";

function RoleSelection() {
  const navigate = useNavigate();

  const redirectTo = (path) => {
    navigate(path);
  };

  return (
    <Container className="d-flex vh-100 justify-content-center align-items-center bg-ec-dark-green">
      <Row className="w-100">
        <Col md={6} className="mx-auto text-center">
          <div className="mb-4">
            <img src={logoUrl} alt="EcoCredit Logo" className="logo" />
            <h1 className="title text-ec-grey">EcoCredit</h1>
          </div>
          <Row className="role-container py-4 bg-ec-grey">
            <Col>
              <Button
                variant="ec-medium-green"
                className="role-button"
                onClick={() => redirectTo("/customer/pickup-schedule")}
              >
                <FontAwesomeIcon
                  className="fa-icon"
                  icon={faUser}
                  size="2xl"
                  style={{ color: "#dad7cd" }}
                />
              </Button>
              <h3 className="mt-3 text-ec-dark-green">Customer</h3>
            </Col>
            <Col>
              <Button
                variant="ec-medium-green"
                className="role-button"
                onClick={() => redirectTo("/recycler/active-bids")}
              >
                <FontAwesomeIcon
                  className="fa-icon"
                  icon={faRecycle}
                  size="2xl"
                  style={{ color: "#dad7cd" }}
                />
              </Button>
              <h3 className="mt-3 text-ec-dark-green">Recycler</h3>
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  );
}

export default RoleSelection;
