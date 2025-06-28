// src/features/commonPages/HomePage.jsx
import { Link } from "react-router-dom";
import HeroIllustration from "../assets/HomePage.Hero.Illustration.svg"; // Adjust the path as necessary
import ProfileImage from "../assets/HomePage.Step1.Profile.svg"; // Adjust the path as necessary
import SearchingImage from "../assets/HomePage.Step2.Searching.svg"; // Adjust the path as necessary
import CollaborationImage from "../assets/HomePage.Step3.Collaboration.svg"; // Adjust the path as necessary

export default function HomePage() {
  return (
    <div className="bg-gray-900 text-white min-h-screen font-sans">
      {/* HERO SECTION */}
      <section className="max-w-7xl mx-auto px-4 py-24 flex items-center justify-between">
        <div className="w-1/2">
          <h1 className="text-5xl font-bold leading-tight text-white mb-6">
            Find your next opportunity
          </h1>
          <p className="text-gray-300 text-lg mb-8">
            Join <span className="text-blue-500 font-semibold">SkillSpace</span> to connect with top employers and grow your career.
          </p>
          <div className="flex space-x-4">
            {/* Nút đăng ký */}
            <Link
              to="/register"
              className="bg-blue-600 text-white px-6 py-3 rounded-md hover:bg-blue-700 transition"
            >
              Get Started
            </Link>

            {/* Nút đăng nhập */}
            <Link
              to="/login"
              className="border border-blue-500 text-blue-500 px-6 py-3 rounded-md hover:bg-blue-600 hover:text-white transition"
            >
              Log in
            </Link>
          </div>
        </div>
        <div className="w-1/2 hidden md:block bg-gray-800 p-6 rounded-lg shadow-lg">
          <img
            src={HeroIllustration}
            alt="Hero Illustration"
            className="w-full max-w-md mx-auto"
          />
        </div>
      </section>

      {/* HOW IT WORKS SECTION */}
      <section className="bg-gray-800 py-20">
        <div className="max-w-6xl mx-auto px-4">
          <h2 className="text-3xl font-bold text-white text-center mb-12">
            How It Works
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-12 text-center">
            {/* Step 1 */}
            <div className="bg-gray-700 p-6 rounded-lg shadow-lg">
              <img
                src={ProfileImage}
                alt="Step 1"
                className="w-32 h-32 mx-auto mb-4"
              />
              <h3 className="text-xl font-semibold text-blue-500 mb-2">
                Create your profile
              </h3>
              <p className="text-gray-300">
                Sign up and showcase your skills, experience, and goals to recruiters.
              </p>
            </div>

            {/* Step 2 */}
            <div className="bg-gray-700 p-6 rounded-lg shadow-lg">
              <img
                src={SearchingImage}
                alt="Step 2"
                className="w-32 h-32 mx-auto mb-4"
              />
              <h3 className="text-xl font-semibold text-blue-500 mb-2">
                Search and apply for jobs
              </h3>
              <p className="text-gray-300">
                Browse job listings tailored to your profile and apply instantly.
              </p>
            </div>

            {/* Step 3 */}
            <div className="bg-gray-700 p-6 rounded-lg shadow-lg">
              <img
                src={CollaborationImage}
                alt="Step 3"
                className="w-32 h-32 mx-auto mb-4"
              />
              <h3 className="text-xl font-semibold text-blue-500 mb-2">
                Connect with recruiters
              </h3>
              <p className="text-gray-300">
                Chat and schedule interviews with top employers directly on the platform.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* CTA */}
      <section className="bg-gray-900 py-16">
        <div className="text-center max-w-2xl mx-auto">
          <h2 className="text-2xl font-bold mb-4 text-white">
            Ready to make your next move?
          </h2>
          <p className="text-gray-400 mb-6">
            Sign up today and take your career to the next level with SkillSpace.
          </p>
          <Link
            to="/register"
            className="bg-blue-600 text-white px-8 py-3 rounded-md hover:bg-blue-700 transition"
          >
            Sign up to get started
          </Link>
        </div>
      </section>

      {/* FOOTER */}
      <footer className="bg-gray-800 text-gray-400 text-sm py-6 text-center">
        © {new Date().getFullYear()} SkillSpace. All rights reserved.
      </footer>
    </div>
  );
}