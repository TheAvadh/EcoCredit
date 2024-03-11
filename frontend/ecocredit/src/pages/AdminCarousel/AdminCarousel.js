import Carousel from 'react-bootstrap/Carousel';
import './AdminCarousel.css';

const AdminCarousel = () => {
    return (
        <Carousel>
        <Carousel.Item interval={1000}>
          <a href="/view-pickups">
            <img src='https://miro.medium.com/v2/resize:fit:1400/0*b4jhv2frpoSQ9fD2.png' 
            className='d-block w-100'
                alt='trash pickup'/>
          </a>
          <Carousel.Caption className="carousel-caption">
            <h3>View Pickups</h3>
          </Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item interval={1000}>
          <a href="/view-bids">
            <img src='https://freestar.com/wp-content/uploads/2023/06/How-Does-In-App-Header-Bidding-Work-Matt.png'  
            className = 'd-block w-100'
            alt='bids' />
          </a>
          <Carousel.Caption>
            <h3>View bids</h3>
          </Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item interval={1000}>
          <a href="/create-a-bid">
            <img src='https://media.istockphoto.com/id/1319023983/photo/announcement-concept-cheerful-black-woman-shouting-with-megaphone-in-hands-blue-background.jpg?s=612x612&w=0&k=20&c=SF-qv1Saei8cgKapMAnwqeYM8FftgMDt-eepUi4Nd_E=' 
            className = 'd-block w-100'
            alt='create-bid' />
          </a>
          <Carousel.Caption>
            <h3>Create a Bid</h3>
          </Carousel.Caption>
        </Carousel.Item>
      </Carousel>
    );
};

export default AdminCarousel;