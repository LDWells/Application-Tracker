


function StatusColor({status})
{
	if (status === "APPLIED" || status === "PENDING")
	{
		return (<span className='text-warning'>{status}</span>);
	}
	else if (status === "REJECTED")
	{
		return (<span className='text-danger'>{status}</span>);
	}
	else if (status === "INTERVIEW")
	{
		return (<span className='text-info'>{status}</span>);
	}
	else if (status === "OFFER" || status === "COMPLETED")
	{
		return (<span className='text-success'>{status}</span>);
	}
	else
	{
		return (<></>);
	}
};
export default StatusColor;