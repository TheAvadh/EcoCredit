import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Table from "react-bootstrap/Table";
import "../../components/PickupScheduleContainer/PickupScheduleContainer.css";

const PickupStatus = () => {
  return (
    <Container fluid className="background-image">
      <Row className="d-flex justify-content-center align-items-center">
        <Col lg={6} className="mt-5">
          <div className="shadow-lg rounded-4 bg-ec-grey p-3">
            <h1 className="text-center text-ec-dark-green p-2">
              Pickup Status
            </h1>
            <Table
              striped
              bordered
              hover
              variant="ec-light-green"
              className="table-custom mb-0"
            >
              <thead>
                <tr>
                  <th>#</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>2024-03-08</td>
                  <td>12:30</td>
                  <td>SCHEDULED</td>
                </tr>
                <tr>
                  <td>2</td>
                  <td>2024-03-07</td>
                  <td>10:15</td>
                  <td>IN-PROGRESS</td>
                </tr>
                <tr>
                  <td>3</td>
                  <td>2023-02-29</td>
                  <td>15:45</td>
                  <td>CANCELED</td>
                </tr>
              </tbody>
            </Table>
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default PickupStatus;
