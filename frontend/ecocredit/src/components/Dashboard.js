import React from "react";
import { NavLink, Routes, Route } from "react-router-dom";
import Nav from "react-bootstrap/Nav";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const Dashboard = ({ tabs, children }) => {
  return (
    <Row className="m-0" style={{ height: "100vh" }}>
      <Col md={2} className="bg-ec-dark-green">
        <Nav
          variant="pills"
          className="sticky-top flex-column fs-6 fw-medium pt-3"
        >
          {tabs.map((tab, index) => (
            <Nav.Item key={index} className="mb-2">
              <Nav.Link as={NavLink} to={tab.to} activeclassname="active">
                <FontAwesomeIcon icon={tab.icon} className="fa-icon pe-2" />
                {tab.label}
              </Nav.Link>
            </Nav.Item>
          ))}
        </Nav>
      </Col>
      <Col md={10} className="px-0">
        <Routes>
          {tabs.map((tab, index) => (
            <Route key={index} path={tab.path} element={tab.component} />
          ))}
          {children}
        </Routes>
      </Col>
    </Row>
  );
};

export default Dashboard;
