import Dashboard from "../../../components/Dashboard";
import {
    faCalendarCheck,
    faCheck,
    faPenToSquare
  } from "@fortawesome/free-solid-svg-icons";
import ScheduledPickups from "../Pickup/Scheduled/ScheduledPickups";
import CompletedPickups from "../Pickup/Completed/CompletedPickups";
import CreateBid from "../Bid/Create/CreateBid";

const AdminDashboard = () => {
    const tabs = [
        {
            label: "Scheduled Pickups",
            to: "/admin/scheduled-pickups",
            path: "/scheduled-pickups",
            icon: faCalendarCheck,
            component: <ScheduledPickups />,
          },
        {
          label: "Completed Pickups",
          to: "/admin/completed-pickups",
          path: "/completed-pickups",
          icon: faCheck,
          component: <CompletedPickups />,
        },
        {
          label: "Create Bid",
          to: "/admin/create-bid",
          path: "/create-bid",
          icon: faPenToSquare,
          component: <CreateBid />,
        }
    ];

    return (
      <Dashboard tabs={tabs}>
      </Dashboard>
    );
};

export default AdminDashboard;