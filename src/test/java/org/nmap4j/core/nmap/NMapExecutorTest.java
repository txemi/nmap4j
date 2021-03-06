package org.nmap4j.core.nmap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nmap4j.NmapTestUtils;
import org.nmap4j.core.flags.ArgumentProperties;
import org.nmap4j.core.flags.Flag;

import static org.junit.Assert.fail;

public class NMapExecutorTest {

	private ArgumentProperties nmapArgs;
	private NMapProperties nmapProps;

	@Before
	public void setup() {
		nmapArgs = new ArgumentProperties();
		nmapProps = new NMapProperties();
	}

	@After
	public void teardown() {
		nmapArgs = null;
		nmapProps = null;
	}

	/**
	 * This tests whether or not we fail with an exception when we try
	 * to instantiate the object with nulls which isn't a valide use case.
	 */
	@Test
	public void testConstructionWithNulls() {
		boolean hasFailed = false;
		// expect this to throw an exception
		try {
			NMapExecutor nmapExec = new NMapExecutor( null, null );
		} catch (NMapInitializationException e) {
			// this is what I would expect to happen
			hasFailed = true;
		}

		if (!hasFailed) {
			fail();
		}
	}

	/**
	 * This test verifies that the initialization fails due to there being
	 * no path to nmap.
	 */
	@Test
	public void testConstructionWithNoPath() {
		boolean hasFailed = false;
		// expect this to throw an exception
		try {
			NMapExecutor nmapExec = new NMapExecutor( nmapArgs, nmapProps );
		} catch (NMapInitializationException e) {
			// this is what I would expect to happen
			hasFailed = true;
		}

		if (!hasFailed) {
			fail();
		}
	}

	@Test
	public void testZeroLengthNMapPath() {
		nmapProps.setPath( "" );

		boolean hasFailed = false;
		// expect this to throw an exception
		try {
			NMapExecutor nmapExec = new NMapExecutor( nmapArgs, nmapProps );
		} catch (NMapInitializationException e) {
			// this is what I would expect to happen
			hasFailed = true;
		}

		if (!hasFailed) {
			fail();
		}
	}

	@Test
	public void testExecuteMethodWithoutSudo() throws NMapInitializationException, NMapExecutionException {

		nmapProps.setPath( NmapTestUtils.findNmapPath() );

		nmapArgs.addFlag( Flag.AGGRESIVE_TIMING );
		nmapArgs.addFlag( Flag.VERSION );
		nmapArgs.addFlag( Flag.OS_DETECTION );

		nmapArgs.addIncludedHost( "localhost" );


		NMapExecutor nmapExec = new NMapExecutor( nmapArgs, nmapProps );
		ExecutionResults results = nmapExec.execute();
		System.out.println( results.getOutput() );

	}

}
