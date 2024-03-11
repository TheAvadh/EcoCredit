import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

const AdminNavbar = () => {
    return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container fluid>
        <Navbar.Brand href="#">EcoCredit</Navbar.Brand>
        <Navbar.Toggle aria-controls="ecoCreditBrand" />
        <Navbar.Collapse id="ecoCreditBrand">
          <Nav
            className="me-auto my-2 my-lg-0"
            style={{ maxHeight: '100px' }}
            navbarScroll
          >
            <Nav.Link href="#action2">View Pickups</Nav.Link>
            <Nav.Link href="#action2">View Bids</Nav.Link>
            <Nav.Link href="#action2">Create a Bid</Nav.Link>
          </Nav>
          <Button variant="outline-success" className="d-flex">
            Log out
          </Button>
        </Navbar.Collapse>
      </Container>
    </Navbar>
    );
};

export default AdminNavbar;