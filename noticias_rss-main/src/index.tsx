import React from 'react';
// @ts-ignore
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import store from './redux/store';
import NewsList from './NewsList';

ReactDOM.render(
    <Provider store={store}>
        <NewsList />
    </Provider>,
    document.getElementById('root')
);
