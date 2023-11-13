import React from 'react';

const Footer = () => {
  return (
    <footer className="footer navbar-dark bg-dark fixed-bottom">
      <div className="container">
        <div className="row">
          <div className="col-md-4"></div>
          <div className="col-md-4">
            <p className="text-center text-muted mt-2">&copy;
              <a href="https://tucanoo.com" className="text-white">Tucanoo Solutions Ltd.</a>
            </p>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;