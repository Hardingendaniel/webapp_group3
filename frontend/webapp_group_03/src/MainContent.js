import {Route, Routes} from "react-router-dom";
import {FrontPage} from "./pages/frontpage/FrontPage";
import {BrowsePage} from "./pages/browsepage/BrowsePage"
import {HotelPage} from "./pages/hotelpage/HotelPage"
import {SignupPage} from "./pages/SignupPage";

/**
 * Represents the main content of the page
 * @return {JSX.Element}
 * @constructor
 */
export function MainContent() {
    return (
        <main>
            <Routes>
                <Route path="/" element={<FrontPage/>}/>
                <Route path="/browse" element={<BrowsePage/>}/>
                <Route path="/hotel" element={<HotelPage/>}/>
                <Route path="/signup" element={<SignupPage/>}/>
            </Routes>
        </main>
    );
}