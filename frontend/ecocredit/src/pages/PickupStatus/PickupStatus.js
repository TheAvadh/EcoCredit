import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import Cookies from "js-cookie";
import "../CustomerDashboard/CustomerDashboard.css";

const PickupStatus = () => {
  const [pickups, setPickups] = useState([]);

  useEffect(() => {
    fetchPickups();
  }, []);

  const fetchPickups = async () => {
    try {
      const response = await fetch(
        `${process.env.REACT_APP_BASE_URL}/pickups/getpickups`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${Cookies.get("token")}`,
            Accept: "application/json",
          },
        }
      );
      const data = await response.json();
      const sortedPickups = data.sort((a, b) => b.pickupId - a.pickupId);
      setPickups(sortedPickups);
    } catch (error) {
      console.error("Error fetching pickups:", error);
    }
  };

  const cancelPickup = async (pickupId) => {
    try {
      const response = await fetch(
        `${process.env.REACT_APP_BASE_URL}/pickups/cancel`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${Cookies.get("token")}`,
          },
          body: JSON.stringify(pickupId),
        }
      );
      if (!response.ok) throw new Error("Failed to cancel the pickup");
      await fetchPickups();
    } catch (error) {
      console.error("Error cancelling the pickup:", error);
    }
  };

  return (
    <Container fluid className="background-image">
      <Row className="d-flex justify-content-center align-items-center">
        <Col lg={8} className="mt-5">
          <div className="shadow-lg rounded-4 bg-ec-grey p-4 mb-5">
            <h1 className="text-center text-ec-dark-green p-2">
              Pickup Status
            </h1>
            <Table
              striped
              bordered
              hover
              variant="ec-grey"
              className="table-custom mb-0"
            >
              <thead>
                <tr>
                  <th>#</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {pickups.map((pickup, index) => (
                  <tr key={pickup.pickupId}>
                    <td>{index + 1}</td>
                    <td>{pickup.pickupDate}</td>
                    <td>{pickup.pickupTime}</td>
                    <td>{pickup.pickupStatus}</td>
                    <td className="text-center">
                      {(pickup.pickupStatus === "SCHEDULED" ||
                        pickup.pickupStatus === "IN_PROGRESS") && (
                        <Button
                          variant="ec-dark-green"
                          onClick={() => cancelPickup(pickup.pickupId)}
                        >
                          Cancel
                        </Button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default PickupStatus;
