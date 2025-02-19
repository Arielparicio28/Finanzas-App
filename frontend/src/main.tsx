import { createRoot } from 'react-dom/client'
import App from './App.tsx'

import { QueryClient, QueryClientProvider } from 'react-query';


// Crear el QueryClient
const queryClient = new QueryClient();


createRoot(document.getElementById('root')!).render(

<QueryClientProvider client={queryClient}>
    <App />
  </QueryClientProvider>

)
