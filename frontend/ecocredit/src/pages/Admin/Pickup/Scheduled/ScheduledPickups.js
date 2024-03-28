import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import Cookies from "js-cookie";
import "../Styling/Pickups.css";
import WeightUpdateForm from "../../Waste/WeightUpdateForm";

const ScheduledPickups = () => {
    const [pickups, setPickups] = useState([]);

    useEffect(() => {
      fetchPickups();
    }, []);
  
    const fetchPickups = async () => {
      fetch(
        `${process.env.REACT_APP_BASE_URL}/admin/scheduled-pickups`,
        {
          method: "GET",
          mode: "cors",
          headers: {
            Authorization: `Bearer ${Cookies.get("token")}`,
            Accept: "application/json"
          },
        }
      )
        .then((response) => response.json())
        .then((data) => {
          setPickups(data);
          console.log(data);
        })
        .catch((error) =>
          console.error("Fetching scheduled pickups data failed:", error)
        );
      };

      const completePickup = async (pickupId) => {
        try {
          const response = await fetch(
            `${process.env.REACT_APP_BASE_URL}/pickups/complete`,
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${Cookies.get("token")}`,
              },
              body: JSON.stringify(pickupId),
            }
          );
          if (!response.ok) throw new Error("Failed to complete pickup");

          await fetchPickups();
        } catch (error) {
          console.error("Error completing the pickup:", error);
        }
        // Reload page after completing pickup
        window.location.reload();
      };

return (
    
    <Container fluid className="background-image">
      <Row className="d-flex justify-content-center align-items-center">
        <Col lg={8} className="mt-5">
          <div className="shadow-lg rounded-4 bg-ec-grey p-4 mb-5">
            <h1 className="text-center text-ec-dark-green p-2">
              Scheduled Pickups
            </h1>
            <div className="scheduled-pickups-table-wrapper">
            <Table
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
                  <th>Username</th>
                  <th>Waste</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {pickups.map((pickup, index) => (
                  <tr key={pickup.id}>
                    <td>{index + 1}</td>
                    <td>{pickup.date}</td>
                    <td>{pickup.time}</td>
                    <td>{pickup.userFirstName + " " + pickup.userLastName}</td>
                    <Table
                        border-collapse
                        hover
                        variant="ec-grey"
                        className="table-custom mb-0 mt-0">
                        <tbody>
                            {pickup.wastes.map((waste) => (
                                <tr key={waste.wasteId}>
                                    <td className="col-widths-id">id: {waste.wasteId}</td>
                                    <td className="col-widths-category">{waste.category}</td>
                                    <td className="col-widths-weight">{waste.weight ? waste.weight : 0.0} kg</td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                    <td className="text-center">
                      {
                        <Button
                          variant="ec-dark-green"
                          size="sm"
                          margin-top="0"
                          margin-bottom="0"
                          onClick={() => completePickup(pickup.id)}
                        >
                          Complete
                        </Button>
                      }
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
            </div>
          </div>
        </Col>
      </Row>
      <WeightUpdateForm></WeightUpdateForm>
      </Container>
    );
};


export default ScheduledPickups;