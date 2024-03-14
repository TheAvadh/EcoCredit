import React from "react";
import { NavLink, Routes, Route } from "react-router-dom";
import Nav from "react-bootstrap/Nav";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import PickupSchedule from "../PickupSchedule/PickupSchedule";
import PickupStatus from "../PickupStatus/PickupStatus";
import ProfilePage from "../ProfilePage/ProfilePage";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCalendarCheck,
  faClockRotateLeft,
  faIdCard,
} from "@fortawesome/free-solid-svg-icons";

const CustomerDashboard = () => {
  return (
    <Row className="m-0" style={{ height: "100vh" }}>
      <Col md={2} className="p-3">
        <Nav variant="pills" className="flex-column fs-5 fw-medium">
          <Nav.Item className="mb-2">
            <Nav.Link
              as={NavLink}
              to="/customer/pickup-schedule"
              activeClassName="active"
            >
              <FontAwesomeIcon
                className="fa-icon pe-2"
                icon={faCalendarCheck}
              />
              Pickup Schedule
            </Nav.Link>
          </Nav.Item>
          <Nav.Item className="mb-2">
            <Nav.Link
              as={NavLink}
              to="/customer/pickup-status"
              activeClassName="active"
            >
              <FontAwesomeIcon
                className="fa-icon pe-2"
                icon={faClockRotateLeft}
              />
              Pickup Status
            </Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link as={NavLink} to="/profile-page" activeClassName="active">
              <FontAwesomeIcon className="fa-icon pe-2" icon={faIdCard} />
              Profile
            </Nav.Link>
          </Nav.Item>
        </Nav>
      </Col>
      <Col md={10} className="px-0">
        <Routes>
          <Route path="/pickup-schedule" element={<PickupSchedule />} />
          <Route path="/pickup-status" element={<PickupStatus />} />
          <Route path="/profile-page" element={<ProfilePage />} />
        </Routes>
      </Col>
    </Row>
  );
};

export default CustomerDashboard;
